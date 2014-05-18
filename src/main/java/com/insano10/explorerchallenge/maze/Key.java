package com.insano10.explorerchallenge.maze;

public class Key
{
    private final String password;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (password != null ? !password.equals(key.password) : key.password != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return password != null ? password.hashCode() : 0;
    }

    public Key(String password)
    {
        this.password = password;
    }
}
