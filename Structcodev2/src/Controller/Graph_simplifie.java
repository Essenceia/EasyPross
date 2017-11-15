
package Controller;



public class Graph_simplifie {
    private int id;
    private String description; 
    private int type;
    private int bitsize;
    private int[] entree;
    private int[] sortie;
    
    public Graph_simplifie()
    {
        id=0;
        description=" ";
        type=0;
        bitsize=0;
        entree = null;
        sortie = null;
    }
    
    public int getid()
    {
        return id;
    }
    public int gettype()
    {
        return type;
    }
    public String getdescrip()
    {
        return description;
    }
    public void setID(int ID)
    {
        id=ID;
    }
    public void setType(int Type)
    {
        type=Type;
    }
    public void setBit(int bit)
    {
        bitsize=bit;
    }
    public void setdescrip(String desc)
    {
        description= desc;
    }
    public void setentree(int[] ent)
    {
        entree=new int[ent.length];
        entree = ent;
    }
    public void setsortie(int[] sort)
    {
        sortie=new int[sort.length];
        sortie = sort;
    }
}
