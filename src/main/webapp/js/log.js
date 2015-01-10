function Log(disableLog)
{
    var logLine = "";

    this.storeLog = function storeLog(contentLine)
    {
        logLine += "<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>";
    };

    this.writeLog = function writeLog()
    {
        $("#log-scroll").append(logLine);
        logLine = "";

        var logScroll = document.getElementById("log-scroll");
        logScroll.scrollTop = logScroll.scrollHeight;
    };

    this.clear = function clear()
    {
        logLine = "";
        $("#log-scroll").html("");
    }
}
