import React, { Component } from 'react';

export default class MyResultWidget extends Component {

    // componentDidMount() {
    //     var player;
    //     window.onYouTubePlayerAPIReady = function() {
    //         player = new YT.Player('player', {
    //             playerVars: {rel:0, autoplay:0},
    //             width: '600',
    //             height: '400',
    //             videoId: 'c0H_qGSJKzE'
    //         });
    //     }
    //     var current = ''
    //     var times = [17.5, 18.5, 19.2, 19.8, 20.3, 20.7]
    //     var stop = times[0]
    //     var started = false
    //     function onPlayerStateChange(evt) {
    //         if (evt.data == YT.PlayerState.PLAYING) {
    //             if (started) return
    //             started = true
    //             var success = false
    //             var zundoko = function() {
    //                 var t = player.getCurrentTime()
    //                 if (t >= stop) {
    //                     if (current == 'ズンズンズンズンドコ') {
    //                         document.getElementById('log').textContent += 'キ・ヨ・シ！'
    //                         player.setPlaybackRate(1)
    //                         success = true
    //                     } else {
    //                         var next = ['ズン', 'ドコ'][Math.floor(Math.random() * 2)]
    //                         current += next
    //                         document.getElementById('log').textContent += next
    //                         var seek;
    //                         if (next == 'ズン') {
    //                             var numOfZum = current.length / 2
    //                             // 5回目のズンならやり直し
    //                             if (numOfZum > 4) {
    //                                 current = ''
    //                                 seek = times[0]
    //                                 stop = times[1]
    //                             } else {
    //                                 // ズンを続ける場合はシークしない
    //                                 seek = numOfZum - 1 ? 0 : times[numOfZum - 1]
    //                                 stop = times[numOfZum]
    //                             }
    //                         } else if (current != 'ズンズンズンズンドコ') {
    //                             // やり直し
    //                             current = ''
    //                             seek = times[4]
    //                             stop = times[5]
    //                         }
    //                         if (seek) {
    //                             //player.setPlaybackRate(0.5)
    //                             player.seekTo(seek, true)
    //                         }
    //                     }
    //                 }
    //                 if (!success) setTimeout(zundoko, 10)
    //             }
    //             setTimeout(zundoko, 10)
    //         }
    //     }
    //
    //     var tag = document.createElement('script');
    //     tag.src = "https://www.youtube.com/iframe_api";
    //     var firstScriptTag = document.getElementsByTagName('script')[0];
    //     firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
    // }

    shouldComponentUpdate() {
        return false;
    }

    render() {
        return (<div id="player"></div>);
    }
}
