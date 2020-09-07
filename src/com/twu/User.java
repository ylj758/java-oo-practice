package com.twu;

import java.io.*;
import java.util.*;

/**
 * @author
 * @version 1.0
 * @date 2020/8/30 0030 9:06
 **/
public class User {
    public int role;//0普通用户，1管理员
    public String username;
    public String password;
    public int leftVote;

    public User() {
    }

    public User(int role, String username, String password, int leftVote) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.leftVote = leftVote;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLeftVote() {
        return leftVote;
    }

    public void setLeftVote(int leftVote) {
        this.leftVote = leftVote;
    }

    private static Map<String, User> userMap;
    private static Map<String, HotSearch> hotSearchMap;
    private final File hotSearchFile = new File("F:\\TW相关\\TWU-C Term4\\新建文件夹\\java-oo-practice-master\\src\\com\\twu\\resources\\hotSearch.txt");
    private final File userFile = new File("F:\\TW相关\\TWU-C Term4\\新建文件夹\\java-oo-practice-master\\src\\com\\twu\\resources\\user.txt");


    public User findUserInfo(String username) throws Exception {
        userMap = readUserInfoFromFile();
        return userMap.get(username);
    }

    public void viewHotSearchRank() throws Exception{
        hotSearchMap = readHotSearchInfoFromFile();
        int hotSearchId = 1;
        Set<String> hotSearchNameSet = hotSearchMap.keySet();
        List<HotSearch> hotSearchSortList = new ArrayList<>();
        hotSearchNameSet.forEach(hotSearchName->hotSearchSortList.add(hotSearchMap.get(hotSearchName)));
        Collections.sort(hotSearchSortList, new Comparator<HotSearch>() {
            @Override
            public int compare(HotSearch o1, HotSearch o2) {
                return o1.getRank() != o2.getRank()?o1.getRank()-o2.getRank(): o2.getVote()-o1.getVote();
            }
        });
        for (HotSearch hotSearch:hotSearchSortList) {
            System.out.println(String.format("%d  %s  %d", hotSearchId++, hotSearch.getName(), hotSearch.getVote()));
        }
    }

    public void voteToHotSearch(User currentUser, String hotSearchName,int vote) throws Exception{
        if(currentUser.getLeftVote()<vote){
            System.out.println("投票失败");
            return;
        }
        userMap = readUserInfoFromFile();
        hotSearchMap = readHotSearchInfoFromFile();
        if(!hotSearchMap.containsKey(hotSearchName)){
            System.out.println("投票失败");
            return;
        }
        hotSearchMap.forEach((hotSearchKey,hotSearch)->{
            if(hotSearchKey.equals(hotSearchName)){
                if(hotSearch.isSuperHotSearch() == 1){
                    hotSearch.setVote(hotSearch.getVote() + vote*2);
                }else{
                    hotSearch.setVote(hotSearch.getVote() + vote*1);
                }
            }
        });
        userMap.forEach((username,user)->{
            if(username.equals(currentUser.getUsername())){
                user.setLeftVote(currentUser.getLeftVote() - vote);
            }
        });
        update(userMap,userFile);
        update(hotSearchMap,hotSearchFile);
    }


    public void addHotSearch(String hotSearchName) throws Exception{
        hotSearchMap = readHotSearchInfoFromFile();
        if(hotSearchMap.containsKey(hotSearchName)){
            System.out.println("添加失败，热搜已存在");
            return;
        }
        HotSearch hotSearch = new HotSearch(hotSearchName);
        hotSearchMap.put(hotSearchName,hotSearch);
        update(hotSearchMap,hotSearchFile);
    }

    public void buyHotSearch(String buyHotSearchName, int buyRank, int buyMoney) throws Exception{
        hotSearchMap = readHotSearchInfoFromFile();
        if(buyRank<0 || buyRank>hotSearchMap.size()){
            System.out.println("购买失败");
            return;
        }
        HotSearch hotSearch = hotSearchMap.get(buyHotSearchName);
        //当前热搜已存在当前排名，购买失败
        if(hotSearch.getRank() == buyRank){
            System.out.println("购买失败");
            return;
        }
        String sameRankHotSearchName = "";
        for(Map.Entry<String,HotSearch> entry : hotSearchMap.entrySet()){
            HotSearch hotSearchElement = entry.getValue();
            if(hotSearchElement.getRank() == buyRank){
                if(hotSearchElement.getBuyMoney() < buyMoney){
                    sameRankHotSearchName = entry.getKey();
                }else{
                    System.out.println("购买失败");
                    return;
                }
            }
        }
//        如果当前热搜已存在排名，但现在重新购买了排名，不会将其删除，只会更新排名和购买金额
        if(!sameRankHotSearchName.isEmpty() && !buyHotSearchName.equals(sameRankHotSearchName)){
            hotSearchMap.remove(sameRankHotSearchName);
        }
        hotSearch.setRank(buyRank);
        hotSearch.setBuyMoney(buyMoney);
        update(hotSearchMap,hotSearchFile);
    }



    public void addSuperHotSearch(String hotSearchName) throws Exception{
        hotSearchMap = readHotSearchInfoFromFile();
        if(hotSearchMap.containsKey(hotSearchName)){
            System.out.println("添加失败，热搜已存在");
            return;
        }
        HotSearch hotSearch = new HotSearch(hotSearchName);
        hotSearch.setSuperHotSearch(1);
        hotSearchMap.put(hotSearchName,hotSearch);
        update(hotSearchMap,hotSearchFile);
    }

    private Map<String, User> readUserInfoFromFile() throws Exception {
        Map<String, User> userMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(userFile)));
        String str = "";
        while ((str = reader.readLine()) != null) {
            String[] userInfo = str.split(",");
            userMap.put(userInfo[1], new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], Integer.parseInt(userInfo[3])));
        }
        return userMap;
    }

    private Map<String, HotSearch> readHotSearchInfoFromFile() throws Exception {
        Map<String, HotSearch> hotSearchMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(hotSearchFile));
        String str = "";
        while ((str = reader.readLine()) != null) {
            String[] hotSearchInfo = str.split(",");
            hotSearchMap.put(hotSearchInfo[0], new HotSearch(hotSearchInfo[0], Integer.parseInt(hotSearchInfo[1]), hotSearchInfo[2], Integer.parseInt(hotSearchInfo[3]), Integer.parseInt(hotSearchInfo[4]), Integer.parseInt(hotSearchInfo[5])));
        }
        return hotSearchMap;
    }

    private void update(Map map,File file) throws Exception{
        StringBuilder sb = new StringBuilder();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        map.forEach((k,v)->{
            sb.append(v.toString()+"\n");
        });
        bw.write(sb.toString().trim());
        bw.close();
    }


    @Override
    public String toString() {
        return role +
                "," + username +
                "," + password +
                "," + leftVote ;
    }
}
