<html>
<body>
       USER is trying to Login! at.....
       <br>
       <br>
       <div id="time">
              </div>
       <br>
       <br>
       <div>
       <marquee>Made with Love By Bhavshresth</marquee>
       </div>


       <script type="text/javascript">

        function updateTime(){
            document.getElementById("time").innerText=new Date().toString();
        }



        //every 1000 ms
        setInterval( updateTime ,1000);

       </script>
</body>
</html>