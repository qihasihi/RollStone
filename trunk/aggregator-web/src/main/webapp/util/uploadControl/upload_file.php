<?php
if ($_FILES["file"]["error"] > 0)
  {
  echo "Error:" . $_FILES["file"]["error"];
  }
else
  {
  echo "ok";
  
  move_uploaded_file($_FILES["file"]["tmp_name"], dirname(__FILE__) . "/" . $_FILES["file"]["name"]);
  }
?>