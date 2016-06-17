package org.jenkinsci.sample;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.annotation.Nonnull;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.console.ConsoleLogFilter;
import hudson.console.LineTransformationOutputStream;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildWrapperDescriptor;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildWrapper;
import org.jenkins.pubsub.EventProps;
import org.jenkins.pubsub.Events;
import org.jenkins.pubsub.MessageException;
import org.jenkins.pubsub.PubsubBus;
import org.jenkins.pubsub.RunMessage;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Kiyotaka Oku
 */
public class MyWrapper extends SimpleBuildWrapper {

    @DataBoundConstructor
    public MyWrapper() {}

    @Override
    public void setUp(Context context, Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener, EnvVars initialEnvironment) throws IOException, InterruptedException {
    }

    @Override
    public ConsoleLogFilter createLoggerDecorator(@Nonnull Run<?, ?> build) {
        System.out.println("Parent Name:" + build.getParent().getName());
        return new MyConsoleLogFilter(build.getParent().getName());
    }

    @Extension
    public static class DescriptorImpl extends BuildWrapperDescriptor {

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "MyDescriptor";
        }
    }

    static class MyConsoleLogFilter extends ConsoleLogFilter implements Serializable {
        private String jobName;

        public MyConsoleLogFilter(String jobName) {
            this.jobName = jobName;
        }

        @Override
        public OutputStream decorateLogger(Run build, OutputStream logger) throws IOException, InterruptedException {
            Job job = (Job) Jenkins.getInstance().getItem(jobName);
            Run run = job.getBuilds().getLastBuild();
            return new MyLineTransformationOutputStream(run, logger, Charset.forName("UTF-8"));
        }
    }

    static class MyLineTransformationOutputStream extends LineTransformationOutputStream {

        private final Run run;
        private final OutputStream out;
        private final Charset charset;

        public MyLineTransformationOutputStream(Run run, OutputStream out, Charset charset) {
            this.run = run;
            this.out = out;
            this.charset = charset;
        }

        @Override
        protected void eol(byte[] b, int len) throws IOException {
            String line = charset.decode(ByteBuffer.wrap(b, 0, len)).toString();
            line = trimEOL(line);


            if (line.startsWith("Entering stage ")) {
                System.out.println(line);
                try {
                    PubsubBus.getBus().publish(new RunMessage(run)
                            .setEventName("job_run_updated")
                    );
                } catch (MessageException e) {
                    e.printStackTrace();
                }
            }

            out.write(b, 0, len);
        }

        @Override
        public void close() throws IOException {
            super.close();
            out.close();
        }
    }
}
