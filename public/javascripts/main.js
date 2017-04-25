$(window).load(function(){
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#imageUpload').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#inputFile").change(function(){
        readURL(this);
    });


    $("#home-article").show("drop", {direction: "up", easing: "swing"}, 800);

    $(".userList").show("drop", {direction: "right", easing: "swing"}, 800);

    $(".profile-gallery").show("drop", {direction: "right", easing: "swing"}, 800);

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





});