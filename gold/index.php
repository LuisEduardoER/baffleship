<?php

// remove this eventually
if (@$_GET['saws'])
{
	header('Content-type: text/plain');
	readfile(basename($_SERVER['PHP_SELF']));
	exit(0);
}

//has $secret and $api_key
//make sure to set this up in a directory apache doesn't serve
require_once('../gold_price_secret.php');

$appid = '198253726224';

//this is the location of the pictures
//same as this php file is fine
$imgurl = 'http://frankrowe.com/goldprice/';
$wallpic = $imgurl+'70x50.jpg';

//should we give these guys credit? 
$feedurl = "http://dgcsc.org/goldprices.xml";

//this needs to be installed, its the facebook-platform.tar stuff
require_once('../facebook-platform/php/facebook.php');

$facebook = new Facebook($api_key, $secret);

// Get the current user's ID (or the Page ID)
if (@$_POST['fb_sig_user'])
{
	// fb_sig_user is set when we are not authorized
	$fb_user = $_POST['fb_sig_user'];

} else if (@$_POST['fb_sig_canvas_user'])
{	
	// fb_sig_canvas_user is set when we are authorized
	$fb_user = $_POST['fb_sig_canvas_user'];

} else if (@$_POST['fb_sig_page_id'])
{
	// the request is on behalf of a page
	$fb_user = $_POST['fb_sig_page_id'];

}

/* If the first argument passed to the script is "generate" then we 
 * go get the current gold price and generate some HTML for it,
 * and then exit
*/
if (@$_GET['generate'] || @$_SERVER['argv'][1] == 'generate')
{
	
	//get the gold price from xml feed
	//eventually we could have multiple feeds in case some go down
    $xml = simplexml_load_file("$feedurl");
	foreach($xml->Price as $price)
        if($price->attributes() == 'USD')
		{
            $price_per_oz = round($price*31.103,2);
			break;
        }       
				
	//colors and styles and shit should be changed to something original
	if ($price_per_oz)
	{
		$FBML = "<span style='font-size: xx-large; color: #3b5998;'>"
				+"<a href='http://www.facebook.com/apps/application.php?id=$appid'><img src='"
				+$wallpic+"'><br>&#36; "+$price_per_oz+" per ounce</span><br /></a>";
	}
	else; //should have something here indicating the xml prices feed has gone down
		
 exit(0);
 
}

// Possibily helpful debug information
//echo "<pre>Debug:" . print_r($facebook,true) . "</pre>";



/* Set the profile box FBML for the user currently viewing the canvas page (but
 * only if we have a UID)
 */
if ($fb_user)
{
	$facebook->api_client->profile_setFBML(NULL, $fb_user, $FBML, NULL,
		$FBML, $FBML);
}

// print out our canvas page: days until gold and add-to-profile button
?>

<fb:dashboard>Gold gold Gold</fb:dashboard>
<?php echo $boxfbml ?>

<fb:if-is-app-user uid='<?php echo $fb_user ?>'>
  <p>Add this application to your profile and never lose track again!</p>
  <fb:add-section-button section='profile' />
<fb:else>
  <p>You must authorize this application before adding it to your profile:</p>
  <form requirelogin=1>
    <input type=submit value='Click to authorize'
           style='background:#3b5998; color:#fff; border:1px solid #0e1f5b;
		  border-left-color:#D9DFEA; border-top-color:#D9DFEA;' />
  </form>
</fb:else>
</fb:if-is-app-user>

