
function Log()
{
    this.updateLog = function updateLog(contentLine)
    {
        $("#logScroll").append("<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>");

        var logScroll = document.getElementById("logScroll");
        logScroll.scrollTop = logScroll.scrollHeight;
    }

    this.clear = function clear()
    {
        $("#logScroll").html("");
    }
}
