public abstract class Filter
{
    public String filtername;
    
    public Filter(){}
    
    public String gibFiltername()
    {
        return filtername;
    }
    
    abstract void anwenden(Farbbild bild);
}
