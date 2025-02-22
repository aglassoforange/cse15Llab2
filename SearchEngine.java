import java.io.IOException;
import java.net.URI;
import java.util.*;


class Handler_new implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    ArrayList<String> friut_list = new ArrayList<String>();
    String re = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Yao Liu's Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                } else if(parameters[0].equals("s")){
                    friut_list.add(parameters[1]);
                    return String.format("this is %s", friut_list.get(0));
                }
            }
            if (url.getPath().contains("/search")) {

                String[] parameters = url.getQuery().split("=");
                for(int i = 0;i < friut_list.size(); i ++){
                    String friut = friut_list.get(i);
                    if(friut.contains(parameters[1])){
                        re= re + " "+friut;
                    }
                }

                return String.format("There is"+ re);


            }
            return "404 Not Found!";
        }
    }
}




class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler_new());
    }
}
