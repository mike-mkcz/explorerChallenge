function Explorer()
{
    /*
        PRIVATE
     */

    var explorerName = '';
    var urlRoot = '';

    /*
        PRIVILEGED
     */

    this.setExplorerHost = function setExplorerHost(chosenHost)
    {
        urlRoot = "http://" + chosenHost + ":8080/explorerchallenge/";
    };

    this.whichWay = function whichWay(fromLocation, availableDirections)
    {
        LOG.updateLog(explorerName + ": which way shall I go?");
        return $.getJSON(urlRoot + "explorer/whichWayNow", {fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
        {
            LOG.updateLog("choosing to go " + data);
        });
    };

    this.getName = function getName()
    {
        return $.getJSON(urlRoot + "explorer/name", function( name )
        {
            LOG.updateLog("Explorer entering the maze is " + name);
            explorerName = name;
        });
    };

    this.enterMaze = function enterMaze(location)
    {
        LOG.updateLog(explorerName + " is entering the maze");
        return $.post(urlRoot + "explorer/enterMaze", {entrance: JSON.stringify(location)});
    };

    this.moveExplorer = function moveExplorer(fromLocation, toLocation)
    {
        LOG.updateLog(explorerName + " is moving from " + JSON.stringify(fromLocation) + " to " + JSON.stringify(toLocation));
        return $.post(urlRoot + "explorer/move", {fromLocation: JSON.stringify(fromLocation), toLocation: JSON.stringify(toLocation)});
    };

    this.exitMaze = function exitMaze()
    {
        LOG.updateLog(explorerName + " is exiting the maze");
        $.post(urlRoot + "explorer/exitMaze");
    };
}


