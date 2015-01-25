function Explorer(sessionId, host)
{
    /*
        PRIVATE
     */

    var id = sessionId;
    var explorerName = '';
    var failedMoveCount = 0;

    var urlRoot = "http://" + host + ":8080/explorerchallenge/";
    var currentLocation = null;
    var hasReachedExit = false;

    var setCurrentLocation = function setCurrentLocation(location)
    {
        currentLocation = location;
    };

    /*
        PRIVILEGED
     */

    this.whichWay = function whichWay(fromLocation, availableDirections)
    {
        LOG.storeLog(explorerName + ": which way shall I go?");
        return $.getJSON(urlRoot + "explorer/whichWayNow", {id: id, fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
        {
            LOG.storeLog("choosing to go " + data);
        });
    };

    this.retrieveName = function retrieveName()
    {
        return $.getJSON(urlRoot + "explorer/name", {id: id}, function( name )
        {
            if(name != null)
            {
                explorerName = name;
            }
            else
            {
                explorerName = "UNKNOWN";
            }
        });
    };

    this.getCurrentLocation = function getCurrentLocation()
    {
        return currentLocation;
    };

    this.enterMaze = function enterMaze(location)
    {
        LOG.storeLog(explorerName + " is entering the maze");
        setCurrentLocation(location);
        hasReachedExit = false;
        failedMoveCount = 0;
        return $.post(urlRoot + "explorer/enterMaze", {id: id, entrance: JSON.stringify(location)});
    };

    this.moveExplorer = function moveExplorer(toLocation)
    {
        var fromLocation = this.getCurrentLocation();
        setCurrentLocation(toLocation);
        LOG.storeLog(explorerName + " is moving from " + JSON.stringify(fromLocation) + " to " + JSON.stringify(toLocation));
        return $.post(urlRoot + "explorer/move", {id: id, fromLocation: JSON.stringify(fromLocation), toLocation: JSON.stringify(toLocation)});
    };

    this.keyFound = function keyFound(key, location)
    {
        LOG.storeLog(explorerName + " found a key at location " + JSON.stringify(location));
        return $.post(urlRoot + "explorer/key", {id: id, key: JSON.stringify(key), location: JSON.stringify(location)});
    };

    this.getAcquiredKey = function getAcquiredKey()
    {
        return $.getJSON(urlRoot + "explorer/key", {id: id}, function(key)
        {
            LOG.storeLog(explorerName + " is going to use key " + JSON.stringify(key));
        });
    };

    this.exitReached = function exitReached(location)
    {
        return $.post(urlRoot + "explorer/exitReached", {id: id, location: JSON.stringify(location)});
    };

    this.exitMaze = function exitMaze()
    {
        hasReachedExit = true;
        LOG.storeLog(explorerName + " is exiting the maze");
        $.post(urlRoot + "explorer/exitMaze", {id: id});
    };

    this.moveSuccessful = function moveSuccessful()
    {
        failedMoveCount = 0;
    };

    this.moveFailed = function moveFailed()
    {
        failedMoveCount++;
    };

    this.shouldContinueTraversal = function shouldContinueTraversal()
    {
        return failedMoveCount < 5 && !hasReachedExit;
    };

    this.toString = function toString()
    {
        var status = explorerName;

        if(failedMoveCount >= 5)
        {
            status += ". And he's stuck at " + JSON.stringify(this.getCurrentLocation());

        }
        return status;
    }
}


