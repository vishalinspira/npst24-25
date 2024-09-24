//password change to text
$(" .toggle-password").click(function () {
  $(this).toggleClass("fa-eye fa-eye-slash");
  input = $(this).parent().find("input");
  if (input.attr("type") == "password") {
    input.attr("type", "text");
  } else {
    input.attr("type", "password");
  }
});

// deshbord toggle js start

$(document).ready(function () {
  $(".hamburger").click(function () {
    $(this).toggleClass("is-active");
    $(".nps-dashboard-wrapper").toggleClass("closed-sidebar");
    $(".submenuList.open").slideToggle(500);
  });
});

// submenuDropdown js start

(function ($) {
  $(document).ready(function () {
    $(".submenuDropdown.active")
      .addClass("active")
      .children(".submenuList")
      .addClass("open")
      .show();
    $(".submenuDropdown #submenuClick").on("click", function () {
      var element = $(this).parent("li");
      if (element.hasClass("active")) {
        element.removeClass("active");
        element.find("li").removeClass("active");
        element.find(".submenuList.open").removeClass("open").slideUp(200);
      } else {
        element.addClass("active");
        element.children(".submenuList").addClass("open").slideDown(200);
        element
          .siblings("li")
          .children(".submenuList")
          .removeClass("open")
          .slideUp(200);
        element.siblings("li").removeClass("active");
        element.siblings("li").find("li").removeClass("active");
        element.siblings("li").find(".submenuList").slideUp(200);
      }
    });
  });
})(jQuery);

// dropdown js start

$(".myDropdown #dropdownClick").on("click", function () {
  $(this).closest(".myDropdown").find(".dropdownList").slideToggle();
  // e.stopPropagation();
});

$(document).on("click", function (e) {
  if (!e.target.closest(".myDropdown")) {
    $(".dropdownList").slideUp();
  }
});

// fix deshbord on scroll js

$(window).scroll(function () {
  if ($(this).scrollTop() > 0) {
    $(".nps-dashboard-wrapper").addClass("deshbordmainFixed");
  } else {
    $(".nps-dashboard-wrapper").removeClass("deshbordmainFixed");
  }
});

// file upload
$("#chooseFile").bind("change", function () {
  var filename = $("#chooseFile").val();
  if (/^\s*$/.test(filename)) {
    $(".file-upload").removeClass("active");
    $("#noFile").text("No file chosen...");
  } else {
    $(".file-upload").addClass("active");
    $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
  }
});

/*
// calender js
$('#calendar').fullCalendar({
  header: {
    left: 'title',
    right: 'prev,next'
  },
  defaultDate: '2022-06-07',
  editable: false,
  eventLimit: true, // allow "more" link when too many events
  

    eventClick: function(event) {
      if (event.url) {
        $.magnificPopup.open({
            items: {                    
              iframe: event.url,
              type: 'iframe'
            }
     
        });
      }
    }
  
});
*/
