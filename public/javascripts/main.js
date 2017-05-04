$(window).load(function(){

    var textWidth;

    $("#new-album").on("click", function () {
        textWidth = $(this).width
        $(this).animate({width: "300px"}, 200)
        $("#album-select").hide();
    })


    $("#notification-article").show("drop", {direction: "up", easing: "swing"}, 800);

    $("#home-article").show("drop", {direction: "up", easing: "swing"}, 800);

    $(".userList").hide();

    $(".userList").show("drop", {direction: "right", easing: "swing"}, 800);

    $(".profile-gallery").show("drop", {direction: "right", easing: "swing"}, 800);

    $(".deleteButton").on("click", function() {
        $.get("/495848-578f83ff-egg8379-yhf398-8uyfh877779h/" + $(this).attr('id'), function() {

        }, "null");

        var image = $(this).parent().parent();
        image.remove();
    });

    $(".deleteButtonImage").on("click", function() {
        $.get("/ldlkfjdf9i93j30sd0fj309sdfj321-2233jfj39-dfd8/" + $(this).attr('id'), function() {

        }, "null");

        var image = $(this).parent().parent();
        image.remove();
    });

    $(".followButton").on("click", function() {
       if($(this).html() === "Follow") {
           $(this).html("Unfollow");
           $.get("/follow/username=" + $(this).attr('id'), function() {

           }, "null");
       } else {
           $(this).html("Follow");
           $.get("/unfollow/username=" + $(this).attr('id'), function() {

           }, "null");

           //var follower = $(this).parent().parent().parent().parent().parent();
           //follower.remove();
       }

    });

    function handleFileSelect2(evt) {
        var files1 = evt.target.files; // FileList object

        // Loop through the FileList and render image files as thumbnails.
        for (var i = 0, f; f = files1[i]; i++) {

            // Only process image files.
            if (!f.type.match('image.*')) {
                continue;
            }

            var reader = new FileReader();

            // Closure to capture the file information.
            reader.onload = (function(theFile) {
                return function(e) {
                    // Render thumbnail.
                    var span = document.createElement('span');
                    span.innerHTML = ['<img class="thumb" src="', e.target.result,
                        '" title="', escape(theFile.name), '"/>'].join('');
                    document.getElementById('list').insertBefore(span, null);
                };
            })(f);

            // Read in the image file as a data URL.
            reader.readAsDataURL(f);
        }
    }

    document.getElementById('files').addEventListener('change', handleFileSelect2, false);

    function handleFileSelect(evt) {
        evt.stopPropagation();
        evt.preventDefault();

        var files = evt.dataTransfer.files; // FileList object.

        // files is a FileList of File objects. List some properties.
        var output = [];
        for (var i = 0, f; f = files[i]; i++) {
            var reader = new FileReader();

            // Closure to capture the file information.
            reader.onload = (function(theFile) {
                return function(e) {
                    // Render thumbnail.
                    var span = document.createElement('span');
                    span.innerHTML = ['<img class="thumb" src="', e.target.result,
                        '" title="', escape(theFile.name), '"/>'].join('');
                    document.getElementById('list').insertBefore(span, null);
                };
            })(f);

            // Read in the image file as a data URL.
            reader.readAsDataURL(f);
        }
        //document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
    }

    function handleDragOver(evt) {
        evt.stopPropagation();
        evt.preventDefault();
        evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
    }

    // Setup the dnd listeners.
    var dropZone = document.getElementById('upload-box-outside');
    dropZone.addEventListener('dragover', handleDragOver, false);
    dropZone.addEventListener('drop', handleFileSelect, false);




});

var imgs = $('.home-thumb');//jQuery class selector

$('img').on('load', function(){

    imgs.each(function(){
        var $this = $(this),
            width = $this.children("img").width(),
            height = $this.children("img").height();

        console.log(width, height);

        if(width < height){
            $this.addClass('portrait');
        }else{
            $this.addClass('landscape');
        }
    })

});