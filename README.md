# Jenkins-Device-Watcher
Jenkins Plugin to notify an administrator when an IP Address becomes unreachable

##Usage
<ol>
<li>Clone Project</li>
<li>Build Project with Maven using <code>hpi:create</code>
  (use <code>hpi:run</code> if you don't have another Jenkins to test on)</li>
<li>Set the IP Addresses you want to watch in the Global Settings</li>
</ol>

When the IP addresses you wish to watch go down, an Administrive Monitor will be created.
