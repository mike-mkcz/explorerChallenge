
function Log()
{
    this.updateLog = function(contentLine)
    {
        $("#logScroll").append("<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>");

        var logScroll = document.getElementById("logScroll");
        logScroll.scrollTop = logScroll.scrollHeight;
    }
}
