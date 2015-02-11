jQuery(function() {
    
	var $ = jQuery;
    

    $(document).ready(function() {
    
        // General styling
        
        $('.wsite-form-radio-container, .wsite-com-product-option-dropdown, .wsite-com-product-option-radio').jqTransform();

        $('body').addClass('postload');

        $( "#nav-trigger" ).change(function() {
          $('body').toggleClass('open');
        });

        // Landing Page
        // --------------------------------------------------------------------------------------//

        setTimeout(function() {
            $(".landing-page #landing-scroll").addClass("loaded");
        }, 1000);
                
        $('.landing-page').waypoint(function() {
            $('.landing-page').addClass('scrolled');
        }, { offset: -5 });
        
        $(".landing-page #landing-scroll").click(function(e){
            e.preventDefault();
            $('.landing-page').addClass('scrolled');
        });

        // Product Page
        // --------------------------------------------------------------------------------------//

        // Swap preview images for hi-res images in product page
        
        $("a.wsite-product-image").each(function(){
            var hires = $(this).attr("href");
            $(this).find('img').attr("src", hires);
        });
        
        
        // Watch for changes on non-mobile nav
        
        $('#navhidden').on('DOMSubtreeModified propertychange', function() {
          $("#navhidden li a").each(function(){
            // Differentiating post-load nav elements by the presence of an id (only currently available modifier)
            if ($(this).attr("id")) {
              var navLinkId = $(this).attr("id");
              var navLinkParent = $(this).parent().detach();

              // Append to mobile nav if new element
              if (!$("#nav #"+navLinkId).length) {
                $("#nav .wsite-menu-default").append(navLinkParent);
              }
            }
          });
        });

    });
});

