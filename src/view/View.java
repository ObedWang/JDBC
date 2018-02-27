package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import action.GoddessAction;
import dao.goddessDao;
import model.Goddess;

public class View {

	private static final String CONTEXT = "欢迎来到女生区：\n"+
			"下面是女生区的功能列表：\n"+
			"[MAIN/M]主菜单\n"+
			"[QUERY/Q]查看全部女生的信息\n"+
			"[GET/G]查看某位女生的信息\n"+
			"[ADD/A]添加女生信息\n"+
			"[UPDATE/U]更新女生信息\n"+
			"[DELETE/D]删除女生信息\n"+
			"[SEARCH/S]查询女生信息（根据姓名、手机号来查询）\n"+
			"[EXIT/E]退出女生区\n"+
			"[BREAK/B]退出当前功能，返回主菜单\n";
	
	private static final String OPERATION_MAIN="MAIN";
	private static final String OPERATION_QUERY="QUERY";
	private static final String OPERATION_GET="GET";
	private static final String OPERATION_ADD="ADD";
	private static final String OPERATION_UPDATE="UPDATE";
	private static final String OPERATION_DELETE="DELETE";
	private static final String OPERATION_SEARCH="SEARCH";
	private static final String OPERATION_EXIT="EXIT";
	private static final String OPERATION_BREAK="BREAK";
	
	private void query(GoddessAction action) {
		try {
			List<Goddess> list = action.query();
			for(Goddess go: list) {
				System.out.println(go.getId()+"号,姓名："+go.getUser_name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void add(GoddessAction action ) {
		Goddess goddess = new Goddess();
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入女神的姓名");	
		int step =1;
		while(true) {
			String in= scan.next();
			if(1==step) {
				goddess.setUser_name(in);
				System.out.println("请输入女神的年龄");
			}
			else if(2==step) {
				goddess.setAge(Integer.valueOf(in));
				System.out.println("请输入女神的生日,格式如:yyyy-MM-dd");
			}
			else if(3==step) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthday = null;
				try {
					birthday = sf.parse(in);
					goddess.setBirthday(birthday);
					System.out.println("请输入女神的邮箱");
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("您输入的格式有误,请重新输入");
					step = 3;
				}
			}
			else if(4==step) {
				goddess.setEmail(in);
				System.out.println("请输入女神的手机号");
			}
			else if(5==step) {
				goddess.setMobile(in);	
				
				try {
					action.add(goddess);
					System.out.println("新增女生成功");
					break;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("新增女生失败");
					break;
				}
			}
			step++;
		}
		
	}
	private void get(GoddessAction action ) {
		System.out.println("请选择你要查看的女神id");
		Scanner in = new Scanner(System.in);
		int id =0;
		while(true) {
			try {
				id = in.nextInt();
				System.out.println(action.get(id).toString());
				break;
			}
			catch(InputMismatchException e){
				System.out.println("请输入一个女生序号数字");
			}
			catch(Exception e) {
				System.out.println("请输入合理范围内的女生序号数字");
			}
		}
	}
	private void delete(GoddessAction action ) {
		System.out.println("请输入删除女神的序列号");
		Scanner in = new Scanner(System.in);
		int id =0;
		try {
			id = in.nextInt();
			action.del(id);
			System.out.println("删除成功！");
		}
		catch(InputMismatchException e){
			System.out.println("请输入一个女生序号数字");
		}
		catch(Exception e) {
			System.out.println("请输入合理范围内的女生序号数字");
		}
	}
	private void search(GoddessAction action) {
		Scanner in = new Scanner(System.in);
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.println("请输入女生信息（姓名）");
		String s = in.next();
		param.put("name", "user_name");
		param.put("rela", "like");
		param.put("value", s);	
		params.add(param);
		try {
			List<Goddess> result =action.query(params);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("请输入正确指令");
		}
	}
	public static void main(String[] args) {
		View view = new View();
		
		System.out.println(CONTEXT);
		//怎么保持程序的持续运行
		Scanner scan =new Scanner(System.in);
		GoddessAction action = new GoddessAction();
		while(scan.hasNext()) {
			String in = scan.next().toString();
			
			if(OPERATION_EXIT.equals(in.toUpperCase())
					||OPERATION_EXIT.substring(0, 1).equals(in.toUpperCase())) {
				System.out.println("您以成功退出女生区。");
				break;
			}
			else if(OPERATION_QUERY.equals(in.toUpperCase())
					||OPERATION_QUERY.substring(0, 1).equals(in.toUpperCase())) {
				view.query(action);
			}
			else if(OPERATION_ADD.equals(in.toUpperCase())
					||OPERATION_ADD.substring(0, 1).equals(in.toUpperCase())) {				
				view.add(action);
			}
			else if(OPERATION_GET.equals(in.toUpperCase())
					||OPERATION_GET.substring(0, 1).equals(in.toUpperCase())) {				
				view.get(action);
			}
			else if(OPERATION_DELETE.equals(in.toUpperCase())
					||OPERATION_DELETE.substring(0, 1).equals(in.toUpperCase())) {				
				view.delete(action);
			}
			else if(OPERATION_SEARCH.equals(in.toUpperCase())
					||OPERATION_SEARCH.substring(0, 1).equals(in.toUpperCase())) {				
				view.search(action);
			}
			else if(OPERATION_MAIN.equals(in.toUpperCase())
					||OPERATION_MAIN.substring(0, 1).equals(in.toUpperCase())) {				
				System.out.println(CONTEXT);
			}
			else {
				System.out.println("您输入的值为："+in);
			}
			
			
			
		}

	}

}
