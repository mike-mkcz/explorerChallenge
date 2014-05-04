var scrollApi;

function initialiseLog()
{
    var settings = {
        showArrows: true
    };
    var pane = $('.scroll');
    pane.jScrollPane(settings);
    scrollApi = pane.data('jsp');
}

function updateLog(contentLine)
{
    scrollApi.getContentPane().append("<p style='padding-left: 10px; margin:0'>" + contentLine + "</p>");
    scrollApi.reinitialise();
    scrollApi.scrollToPercentY(100);
}