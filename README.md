Blue Ocean Extension Point implementation sample plugin.

Please check out the following YouTube video for a walk-through of the concepts relating to client-side
Extension Points in Blue Ocean, as well as a walk-through of this sample plugin.

[![Extension Points Video](https://i.ytimg.com/vi/Oe8jBB_gB0g/hqdefault.jpg)](https://youtu.be/Oe8jBB_gB0g)

And [here are the slides](https://docs.google.com/a/cloudbees.com/presentation/d/1G2_qCAr46FocQnXhU5FI2OshJhBNp870oqkr1VHYBHI/pub?start=false&loop=false&delayms=60000).



# Zundoko pipeline.

<blockquote class="twitter-video" data-lang="ja"><p lang="en" dir="ltr">Oh my this is AMAZING!!!!! <a href="https://twitter.com/jenkinsci">@jenkinsci</a> <a href="https://twitter.com/hashtag/jenkinsstudy?src=hash">#jenkinsstudy</a> <a href="https://twitter.com/hashtag/blueocean?src=hash">#blueocean</a> <a href="https://t.co/CQo8VJfuhi">pic.twitter.com/CQo8VJfuhi</a></p>&mdash; James Dumay (@i386) <a href="https://twitter.com/i386/status/744114125123391488">2016年6月18日</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

### Build hacked blueocean-plugin.

    $ git clone -b myhack git@github.com:kiy0taka/blueocean-plugin.git
    $ cd blueocean-plugin
    $ mvn install -DskipTests

### Run this plugin

    $ git clone -b myhack git@github.com:kiy0taka/blueocean-sample-pipeline-result-ext-plugin.git
    $ cd blueocean-sample-pipeline-result-ext-plugin
    $ mvn hpi:run

### Create pipeline job and run

Zundoko pipeline (This pipeline can not run in the groovy sandbox.)

    node {
        wrap([$class: 'MyWrapper']) {
            stage 'スタート！'
            sh 'sleep 5'
            def random = new Random()
            def current = ''
            while (current != 'ズンズンズンズンドコ') {
                def next = random.nextBoolean() ? 'ズン' : 'ドコ'
                current += next
                stage next
                sh 'sleep 1'
                if (current.size() != 10 && next == 'ドコ') {
                    stage 'やり直し！'
                    current = ''
                }
            }
            stage 'キ・ヨ・シ！'
        }
    }
