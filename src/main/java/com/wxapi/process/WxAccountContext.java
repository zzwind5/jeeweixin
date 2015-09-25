package com.wxapi.process;

import java.util.HashMap;
import java.util.Map;

import com.wxcms.domain.Account;
/**
 * 全局容器，类似于缓存，保存公众号，不需要每次从数据库中读取公众账号
 */

public class WxAccountContext {

	private static Map<String,Account> accountMap = new HashMap<String,Account>();
	
	public static void addAccount(Account account){
		if(account != null && !accountMap.containsKey(account.getAccount())){
			accountMap.put(account.getAccount(), account);
		}
	}
	
	public static void updateAccount(Account account){
		if(account != null && !accountMap.containsKey(account.getAccount())){
			accountMap.put(account.getAccount(), account);
		}
	}
	
	public static Account getAccount(String account){
		return accountMap.get(account);
	}
	
	public static Account getSingleAccount(){
		Account sigleAccount = null;
		for(String key : accountMap.keySet()){
			sigleAccount = accountMap.get(key);
			break;
		}
		return sigleAccount;
	}
	
}


