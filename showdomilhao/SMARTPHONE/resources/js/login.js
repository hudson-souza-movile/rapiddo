// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
        // Logged into your app and Facebook.
            doLogin(response);
    } else if (response.status === 'not_authorized') {
        // The person is logged into Facebook, but not your app.
        document.getElementById('error').innerHTML = 'Please log ' + 'into this app.';
    }
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}

// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
function doLogin(response) {
    $('#social-access-token').prop('value', response.authResponse.accessToken);
    $('#social-id').prop('value', response.authResponse.userID);
    $('#type').prop('value', 2);
    FB.api('/me', {fields: 'email'}, function(response) {
      $('#fbEmail').prop('value', response.email);
        $('#submitButton').click();
    });
}

// LOGOUT
function logout() {
    FB.logout(function(response) {
        console.log(response);
        window.location = "/lp/sdm/default/meus-cupons/?action=logout";

    });
}

// LOGIN
$(document).ready(function() {
    window.fbAsyncInit = function() {
        FB.init({
            appId      : '1062139223867394',
            cookie     : false,  // enable cookies to allow the server to access
                                // the session
            xfbml      : true,  // parse social plugins on this page
            version    : 'v2.5' // use graph api version 2.5
        });

        // FB.login(function(response) {
        //     statusChangeCallback(response);
        // });

    };

    // Load the SDK asynchronously
    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
});
