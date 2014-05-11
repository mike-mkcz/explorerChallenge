
function Log()
{
    var scrollApi;

    this.initialiseLog = function()
    {
        var settings = {
            showArrows: true
        };
        var pane = $('.scroll');
        pane.jScrollPane(settings);
        scrollApi = pane.data('jsp');
    }

    this.updateLog = function(contentLine)
    {
        scrollApi.getContentPane().append("<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>");
        scrollApi.reinitialise();
        scrollApi.scrollToPercentY(100);
    }
}
