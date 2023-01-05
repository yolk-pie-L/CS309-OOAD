

$(document).ready(function () {

    var video = $("video");
    var vidBox = $(".vid-box");
    var playButton = $(".play-button");

    playButton.click(function () {
        if(video.hasClass("pause")){
            video.trigger("play");
            video.removeClass('pause');
            video.addClass('play');
        } else {
            video.trigger("pause");
            video.removeClass('play');
            video.addClass('pause');
        }
    });

    vidBox.mouseover(function () {
        playButton.show();
    });
    vidBox.mouseout(function () {
        playButton.hide();
    });

});



