
function Log()
{
    this.updateLog = function updateLog(contentLine)
    {
        $("#log-scroll").append("<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>");

        var logScroll = document.getElementById("log-scroll");
        logScroll.scrollTop = logScroll.scrollHeight;
    }

    this.clear = function clear()
    {
        $("#log-scroll").html("");
    }
}
