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
            if(name != null)
            {
                explorerName = name;
            }
            else
            {
                explorerName = "UNKNOWN";
            }
            LOG.updateLog("Explorer entering the maze is " + explorerName);
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

    this.keyFound = function keyFound(key, location)
    {
        LOG.updateLog(explorerName + " found a key at location " + JSON.stringify(location));
        return $.post(urlRoot + "explorer/key", {key: JSON.stringify(key), location: JSON.stringify(location)});
    };

    this.getAcquiredKey = function getAcquiredKey()
    {
        return $.getJSON(urlRoot + "explorer/key", function(key)
        {
            LOG.updateLog(explorerName + " is going to use key " + JSON.stringify(key));
        });
    };

    this.exitMaze = function exitMaze()
    {
        LOG.updateLog(explorerName + " is exiting the maze");
        $.post(urlRoot + "explorer/exitMaze");
    };
}


