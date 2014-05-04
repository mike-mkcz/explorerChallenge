var mazeCanvasWidth = 1000;
var mazeCanvasHeight = 800;
var mazeSquareSize = 20;

var mazeMaxXCoordinate;
var mazeMaxYCoordinate;
var mazeXOffsetPx;
var mazeYOffsetPx;

function drawMaze(mazeDefinition)
{
    var mazeCanvas = document.getElementById("maze-canvas");

    //intialise canvas size
    mazeCanvas.width = mazeCanvasWidth;
    mazeCanvas.height = mazeCanvasHeight;

    mazeMaxXCoordinate = mazeDefinition.maxXCoordinate;
    mazeMaxYCoordinate = mazeDefinition.maxYCoordinate;
    var mazeWidthPx = mazeMaxXCoordinate * mazeSquareSize;
    var mazeHeightPx = mazeMaxYCoordinate * mazeSquareSize;
    mazeXOffsetPx = (mazeCanvasWidth - mazeWidthPx)/2;
    mazeYOffsetPx = (mazeCanvasHeight - mazeHeightPx)/2;

    var ctx = mazeCanvas.getContext("2d");
    ctx.clearRect(0, 0, mazeCanvasWidth, mazeCanvasHeight);

    //border
    ctx.beginPath();
    ctx.strokeStyle = "#000000";
    ctx.rect(0,0,mazeCanvasWidth, mazeCanvasHeight);
    ctx.stroke();

    for(var y=0 ; y<=mazeMaxYCoordinate; y++)
    {
        for(var x=0 ; x<=mazeMaxXCoordinate ; x++)
        {
            if(mazeDefinition.grid[x][y])
            {
                ctx.fillStyle = "#FFFFFF";
            }
            else
            {
                ctx.fillStyle = "#000000";
            }

            ctx.beginPath();
            ctx.strokeRect(absoluteXCoordinatePx(x), absoluteYCoordinatePx(y), mazeSquareSize, mazeSquareSize);
            ctx.fillRect(absoluteXCoordinatePx(x), absoluteYCoordinatePx(y), mazeSquareSize, mazeSquareSize);
            ctx.stroke();
        }
    }
}

function drawExplorerLocation(oldLocation, newLocation)
{
    var mazeCanvas = document.getElementById("maze-canvas");
    var ctx = mazeCanvas.getContext("2d");

    ctx.beginPath();
    ctx.strokeStyle = "#FF0000";
    ctx.moveTo(absoluteXCoordinatePx(oldLocation.x) + mazeSquareSize/2, absoluteYCoordinatePx(oldLocation.y) + mazeSquareSize/2);
    ctx.lineTo(absoluteXCoordinatePx(newLocation.x) + mazeSquareSize/2, absoluteYCoordinatePx(newLocation.y) + mazeSquareSize/2);
    ctx.stroke();
}

function absoluteXCoordinatePx(xGridPos)
{
    return mazeXOffsetPx + xGridPos*mazeSquareSize
}

function absoluteYCoordinatePx(yGridPos)
{
    //y origin is reversed from grid to canvas
    var yPosition = (mazeMaxYCoordinate-yGridPos);
    return mazeYOffsetPx + yPosition*mazeSquareSize
}