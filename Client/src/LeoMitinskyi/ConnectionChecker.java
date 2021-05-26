package LeoMitinskyi;

public class ConnectionChecker implements Runnable{

    private boolean isActive;

    public static int t;

    void disable(){
        isActive=false;
    }

    ConnectionChecker(){
        isActive = true;
    }

    @Override
    public void run() {
        t = 0;
        while (isActive){
            t++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (t == 10){
                System.out.println("Нет подключения к серверу.");
                System.out.println("Завершение работы.");
                System.exit(0);
            }
        }
    }
}
