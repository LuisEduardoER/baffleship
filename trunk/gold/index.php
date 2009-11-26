<?php

// remove this eventually
if (@$_GET['source'])
{
	header('Content-type: text/plain');
	readfile(basename($_SERVER['PHP_SELF']));
	exit(0);
}

//make sure to set these up
$secretfile      = '../christmassecret.php';  					 //has $secret and $api_key
$facebookinclude = '../facebook-platform/php/facebook.php';

$appid = '198253726224';
$feedurl = "http://dgcsc.org/goldprices.xml";
          
require_once($facebookinclude);
require_once($secretfile);

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

/* If the first argument passed to the script is "generate" then we calculate
 * the nights to christmas for each of the timezones, update the Fb:ref's
 * corresponding to each, and then exit
*/
if (@$_GET['generate'] || @$_SERVER['argv'][1] == 'generate')
{
	$price_per_oz =0;


    $xml = simplexml_load_file("$feedurl");
	foreach($xml->Price as $price)
        if($price->attributes() == 'USD')
		{
            $price_per_oz = round($price*31.103,2);
			break;
        }       
       
	$FBML .= "<span style='font-size: xx-large; color: #3b5998;'>
			$daystochristmas $sleeps
			</span><br />
			<a
			href='http://www.facebook.com/apps/application.php?id=$appid'>
			<img
			src='http://mretc.net/~cris/facebook/nightstochristmas/snowman-75x75.jpg'
			/>
			</a>";

		// update each fb:ref handle to the new content
		try
		{
			$facebook->api_client->fbml_setRefHandle("tz:$timezone", $FBML);
		}
		catch (Exception $ex)
		{

			/* Sometimes calling this script standalone (like from cron)
			caused setRefHandle to throw an exception about an invalid
			session key.
			
			Seems to be fixed now (11/13/08), but it's good to catch
			exceptions anyhow.
			
			Possible source of this problem:
			http://forum.developers.facebook.com/viewtopic.php?id=24208
			*/
			$facebook->set_user(null, null);
			$facebook->api_client->fbml_setRefHandle("tz:$timezone", $FBML);
		}
	}





 exit(0);
}

// Possibily helpful debug information
//echo "<pre>Debug:" . print_r($facebook,true) . "</pre>";

// Set the FBML for the user currently viewing our canvas page
$boxfbml = "<fb:ref handle='tz:" . get_user_tz() . "' />";

/* Set the profile box FBML for the user currently viewing the canvas page (but
 * only if we have a UID)
 */
if ($fb_user)
{
	$facebook->api_client->profile_setFBML(NULL, $fb_user, $boxfbml, NULL,
		$boxfbml, $boxfbml);
}

// print out our canvas page: days until christmas and add-to-profile button
?>

<fb:dashboard>Nights Until Christmas</fb:dashboard>
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

<?php

/* Calculate the nights until Christmas for each of the 27 whole-hour GMT offset
 * zones: http://en.wikipedia.org/wiki/List_of_time_zones
 * This is called when the script is run with an argument of "generate" which
 * should be done once per hour.
 */



?>
