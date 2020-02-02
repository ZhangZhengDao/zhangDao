/*评论回复*/
function erji(c) {
var b=c.split(",");
var xingming=$("#usernameid"+b[1]).text();
$("#erere1"+b[0]).text("@"+xingming);
}