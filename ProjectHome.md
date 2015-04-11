This is Ant task to execute a JavaScript.

It simplifies combination with fileset.

Using sample:

<pre>
<js><br>
<br>
<fileset dir="src" includes="**/*.java" /><br>
<br>
<param name="prefix" value="^${basedir}/" /><br>
<br>
<![CDATA[<br>
var ite = fileset.iterator();<br>
while (ite.hasNext()) {<br>
var reg = new RegExp(prefix);<br>
var fname = (""+ite.next()).replace(reg,"");<br>
task.log(fname);<br>
}<br>
]]><br>
<br>
<br>
Unknown end tag for </js><br>
<br>
<br>
</pre>