<!-- #Project Raspberry IOT Temperature monitoring -->
<!-- #Server side code to send a POST method to fcm server -->
<!-- #Create by Minh Toan -->
<!-- #Create on 5 October, 2017 -->

<?php
   function sendPushNotificationToGCM($registration_ids, $message) {
   		define('API_ACCESS_KEY', ''
    	);
        $url = 'https://fcm.googleapis.com/fcm/send';
        $msg = array
        (
            'body'  => $message,
            'title' => 'Pi Message',
        );
        $fields = array(
            'registration_ids' => $registration_ids,
            'data' => $msg,
        );
        $headers = array
		(
			'Authorization: key=' . API_ACCESS_KEY,
			'Content-Type: application/json'
		);
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);				
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
        curl_close($ch);
        return $result;
    }
?>
<?php
	$mysql_host = '127.0.0.1';
	$mysql_user = 'root';
	$mysql_pass = 'toanpi';
	$mysql_db  = 'templog';


    $conn = new mysqli($mysql_host, $mysql_user, $mysql_pass, $mysql_db);
	$query = "SELECT * FROM `regids`;";
	$result = $conn->query($query);
	$n = mysqli_num_rows($result);
	$conn->close();
	$RegistrationIDs = array();
	$i = 0;
    $row = $result->fetch_assoc();
	while ($i < $n) {
		$RegistrationIDs[$i] = $row["RegID"];
		$i++;
	}
	$pushMessage = $_POST["mes"];
	$pushStatus = sendPushNotificationToGCM($RegistrationIDs, $pushMessage);
	echo $pushStatus;
?>
