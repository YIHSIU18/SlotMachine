package org.example;

import java.util.List;

public class Program
{
    public static void main(String[] args)
    {
        ColumnsHandler columnsHandler = new ColumnsHandler();
        Machine m = new Machine(columnsHandler, 10);
        m.play();

    }



}