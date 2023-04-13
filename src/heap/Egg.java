package heap;

import jdk.management.resource.ResourceType;

import java.util.*;

/**
 * description:Egg
 * create user: songj
 * date : 2021/4/10 21:10
 */
public class Egg {

    public void stones() {
        String str = "1318073 678016441 874092905 2091308855 763075536 238193668 411460068 512019536 545368023 540157165 1008096286 1575787619 1494177729 2118806932 1214271570 725179549 1122983318 1894335790 1656555755 1754574577 2036958682 2127751547 1222560585 452217599 476559660 1571685957 1277021199 943723475 1993711230 1117298469 838359115 661437838 1410386394 439628372 1490302524 1412745907 1441257717 1750395106 512066289 1331145694 81044612 612161686 7303825 348818896 2116312409 91012802 643806550 1434072264 1243570767 1383028365 194735427 153243561 731636974 131259296 609282403 1007318325 1385498974 916071597 1105065436 1396203596 440027203 1751004200 11690912 1068146107 1515815076 705477971 711043510 1909260662 1243292760 1005532010 1427673827 1079222458 844969044 85364897 208747683 1581512630 1077673491 581284439 744456070 833441068 1755684142 1358064814 1539128582 1693549559 739180775 218387530 389663987 1404989806 2080970677 955493297 93130413 1874756275 1148645141 1548381904 447826182 1831941786 972550263 1154232924 972970317 1771629561 917266072 1853253938 536119878 1872890381 2000819388 325025743 1656748280 695374958 560912132 1944475841 399319641 478809412 728562175 2140203678 52128896 2104510743 1456391511 553516871 58892093 1956929431";
        String[] strings = str.split(" ");
        int[] nums = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i :nums) {
            queue.offer(i);
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            int y = queue.poll();
            if (x > y) {
                queue.offer(x -y);
            }
            if (queue.size() < 6) {

                break;
            }
        }
        List<Integer> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            list.add(queue.poll());
        }
        str = "";
        Collections.reverse(list);
        for (int i : list) {
            str += String.valueOf(i);
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        Egg egg = new Egg();
        egg.stones();
    }
}