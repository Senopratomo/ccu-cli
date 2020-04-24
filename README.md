<h3>CCUv3 CLI</h3>
<hr>
<p>
This is a simple CLI wrapper for Akamai Fast Purge to purge of invalidate CDN content
The purpose is for sample how to use edgegrid JAVA to implement API client on top of
Akamai Fast Purge OPEN API
</p>

<h5>Prerequisite</h5>
<ul>
    <li>Java installed in the loca (note: I use JAVA 11 in this sample, but if you have different JAVA version locally, change the "properties" tag in pom.xml</li>
    <li>Maven installed in local</li>
</ul>
<br>
<h5>Setup</h5>
<ul>
    <li>Clone this project</li>
    <li>Go to that root dir of the project</li>
    <li>Run: mvn clean install</li>
    <li>It will produce file ccu.jar inside "target" folder</li>
</ul>
<br>
<h5>How to use the CLI</h5>
<p>CCU CLI takes 5 arguments separated by single space. These arguments are:</p>
<ul>
   <li>
   args[0] is location of .edgerc file. This file contain Akamai API client credentials (client token,
   access token, secret, host, and max body size) which necessary for EdgeGrid lib
   sample:
   host = https://akab-xxxxx.luna.akamaiapis.net
   client_token = akab-xxxxx
   client_secret = xxxxx
   access_token = xxxx
   </li>
   <li>args[1] is type of purge - options are: 'delete' or 'invalidate'</li>
   <li>args[2] is object to be purge - options are: 'url', 'cpcode', 'tag'</li>
   <li>args[3] is target network - options are: 'staging' or 'production'</li>
   <li>
   args[4] is the object to purge - options are:
   <ol>
    <li>for 'url' object: either single URL surrounded by double-quotes (") or full path to a file containing list of URL (one URL per line)</li>
    <li>for 'cpcode' object: either single or multiple cpcode separated by comma or full path to a file containing list of cpcode (one cpcode per line)</li>
    <li>for 'tag' object: either single cache tag surrounded by double-quotes(") or full path to a file containing list of cpcode (one cpcode per line)</li>
   </ol>
   </li>
</ul>
<br>
<h5>Sample Usage</h5>
<ol>
    <li>java -jar ccu.jar delete cpcode 12345</li>
    <li>java -jar ccu.jar delete url "https:\\www.example.com\index.html"</li>
    <li>
    java -jar ccu.jar delete url /home/user/listof_URL.txt
       where listof_URL.txt contain:
       https:\\www.example.com\index.html
       https:\\www.example.com\style.css
       https:\\www.example.com\app.js
    </li>   
</ol>


