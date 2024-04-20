package JDBC.HospitalManagment;

class Exit extends Thread
{
    @Override
    public void run()
    {
        for(int i=1;i<=6;i++)
        {
            try{
                System.out.print(" ! ");
                Thread.sleep(100);
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
