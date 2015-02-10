package lt.buzzard.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketRefactorer {

    private List<String> paramList;
    private Map<String, Integer> paramMap;

    public PacketRefactorer() {
        paramList = new ArrayList<String>();
        paramMap = new HashMap<String, Integer>();
    }

    public Map<String, Integer> coordMap(String packet) {
        paramList.clear();
        paramMap.clear();
        int a = 0, b = 0, tempa = 0;
        for (char c : packet.toCharArray()) {
            if (c == '$') {
                for (char cc : packet.substring(a + 1).toCharArray()) {
                    if (cc == '$') {
                        tempa = a;
                        b += a + 1;
                        if (packet.substring(a + 1, b).length() != 0) {
                            paramList.add(packet.substring(a + 1, b));
                        }
                        a = tempa;
                        b = 0;
                        break;
                    }
                    b++;
                }
            } else if (c == '#') {
                break;
            }
            a++;
        }

        if (paramList.size() > 0) {
            int x = 0;
            for (String s : paramList) {
                for (char ccc : s.toCharArray()) {
                    if (ccc == '-') {
                        paramMap.put(s.substring(0, x), Integer.parseInt(s.substring(x + 1)));
                        x = 0;
                        break;
                    }
                    x++;
                }
            }
            return paramMap;
        }
        return null;
    }

    public String coordString(int x, int y) {
        return "$x-" + x + "$y-" + y + "$";
    }

}