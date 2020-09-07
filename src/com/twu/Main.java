package com.twu;

import java.io.*;
import java.util.*;

public class Main {

    private static User currentUser;

    public static void main(String[] args) {
        boolean flagEndProgress = true;
        while (flagEndProgress) {
            System.out.println("欢迎来到热搜排行榜，你是？\n1.用户\n2.管理员\n3.退出");
            Scanner sc = new Scanner(System.in);
            int userInput = Integer.parseInt(sc.nextLine());
            if (userInput == 1) {
                boolean flagEndWhile = true;
                while (flagEndWhile) {
                    inputUserInfo(sc);
                    boolean flagEndOperationWhile = true;
                    while (flagEndOperationWhile) {
                        System.out.println(String.format("你好,%s,你可以：", currentUser.getUsername()));
                        System.out.println("1.查看热搜排行榜\n2.给热搜事件投票\n3.购买热搜\n4.添加热搜\n5.退出");
                        int userOption = sc.nextInt();
                        switch (userOption) {
                            case 1:
                                readHotSearchRank();
                                break;
                            case 2:
                                voteToHotSearch(sc);
                                break;
                            case 3:
                                buyHotSearch(sc);

                                break;
                            case 4:
                                addHotSearch(sc);
                                break;
                            case 5:
                                flagEndOperationWhile = false;
                                flagEndWhile = false;
                                break;
                        }
                    }
                }
            } else if (userInput == 2) {
                boolean flagEndWhile = true;
                while (flagEndWhile) {
                    checkUser(sc);
                    boolean flagEndOperationWhile = true;
                    while (flagEndOperationWhile) {
                        System.out.println(String.format("你好,%s,你可以：", currentUser.getUsername()));
                        System.out.println("1.查看热搜排行榜\n2.添加热搜\n3.添加超级热搜\n4.退出");
                        int userOption = sc.nextInt();
                        switch (userOption) {
                            case 1:
                                readHotSearchRank();
                                break;
                            case 2:
                                addHotSearch(sc);
                                break;
                            case 3:
                                addSuperHotSearch(sc);
                                break;
                            case 4:
                                flagEndOperationWhile = false;
                                flagEndWhile = false;
                                break;
                        }
                    }
                }
            } else if (userInput == 3) {
                flagEndProgress = false;
            }
        }
    }

    private static int inputPasswordCount = 2;
    private static void checkUser(Scanner sc) {
        inputUserInfo(sc);
        System.out.println("请输入你的密码：");
        String password = sc.nextLine();
        if(!password.equals(currentUser.getPassword())){
            System.out.println("用户信息错误");
            if(inputPasswordCount<4){
                inputPasswordCount++;
                checkUser(sc);
            }else{
                System.exit(0);
            }
        }
    }

    private static void buyHotSearch(Scanner sc) {
        try {
            System.out.println("请输入你要购买的热搜名称：");
            sc.nextLine();
            String buyHotSearchName = sc.nextLine();
            System.out.println("请输入你要购买的热搜排名：");
            int buyRank = Integer.parseInt(sc.nextLine());
            System.out.println("请输入你要购买的热搜金额：");
            int buyMoney = Integer.parseInt(sc.nextLine());
            currentUser.buyHotSearch(buyHotSearchName, buyRank, buyMoney);
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("购买失败");
        }
    }

    private static void addSuperHotSearch(Scanner sc) {
        System.out.println("请输入你要添加的超级热搜名称：");
        sc.nextLine();
        String addSuperHotSearchName = sc.nextLine();
        try {
            currentUser.addSuperHotSearch(addSuperHotSearchName);
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("添加超级热搜失败");
        }
    }

    private static void voteToHotSearch(Scanner sc) {
        try {
            System.out.println("请输入你要投票的热搜名称：");
            sc.nextLine();
            String hotSearchName = sc.nextLine();
            System.out.println(String.format("请输入你要投票的热搜票数：（你目前还有%d票）", currentUser.getLeftVote()));
            int vote = Integer.parseInt(sc.nextLine());
            currentUser.voteToHotSearch(currentUser, hotSearchName, vote);
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("投票失败");
        }
    }

    private static void addHotSearch(Scanner sc) {
        System.out.println("请输入你要添加的热搜名称：");
        sc.nextLine();
        String addHotSearchName = sc.nextLine();
        try {
            currentUser.addHotSearch(addHotSearchName);
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("添加热搜失败");
        }
    }

    private static void readHotSearchRank() {
        try {
            currentUser.viewHotSearchRank();
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("读取热搜信息失败");
        }
    }

    private static int inputUserNameCount = 2;
    private static void inputUserInfo(Scanner sc) {
        System.out.println("请输入你的昵称：");
        String username = sc.nextLine();
        try {
            currentUser = new User().findUserInfo(username);
            if (currentUser == null) {
                System.out.println("用户信息错误");
                if(inputUserNameCount < 4){
                    inputUserNameCount++;
                    inputUserInfo(sc);
                }else{
                    System.exit(0);
                }
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("用户信息错误");
        }
    }


}

