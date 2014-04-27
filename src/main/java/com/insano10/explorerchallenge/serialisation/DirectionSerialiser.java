package com.insano10.explorerchallenge.serialisation;

import com.google.gson.*;
import com.insano10.explorerchallenge.maze.Direction;

import java.lang.reflect.Type;

public class DirectionSerialiser implements JsonSerializer<Direction>
{
    @Override
    public JsonElement serialize(Direction direction, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject directionObject = new JsonObject();
        directionObject.add("direction", new JsonPrimitive(direction.name()));

        return directionObject;

    }
}
