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

	private static final String CONTEXT = "��ӭ����Ů������\n"+
			"������Ů�����Ĺ����б�\n"+
			"[MAIN/M]���˵�\n"+
			"[QUERY/Q]�鿴ȫ��Ů������Ϣ\n"+
			"[GET/G]�鿴ĳλŮ������Ϣ\n"+
			"[ADD/A]���Ů����Ϣ\n"+
			"[UPDATE/U]����Ů����Ϣ\n"+
			"[DELETE/D]ɾ��Ů����Ϣ\n"+
			"[SEARCH/S]��ѯŮ����Ϣ�������������ֻ�������ѯ��\n"+
			"[EXIT/E]�˳�Ů����\n"+
			"[BREAK/B]�˳���ǰ���ܣ��������˵�\n";
	
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
				System.out.println(go.getId()+"��,������"+go.getUser_name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void add(GoddessAction action ) {
		Goddess goddess = new Goddess();
		Scanner scan = new Scanner(System.in);
		System.out.println("������Ů�������");	
		int step =1;
		while(true) {
			String in= scan.next();
			if(1==step) {
				goddess.setUser_name(in);
				System.out.println("������Ů�������");
			}
			else if(2==step) {
				goddess.setAge(Integer.valueOf(in));
				System.out.println("������Ů�������,��ʽ��:yyyy-MM-dd");
			}
			else if(3==step) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthday = null;
				try {
					birthday = sf.parse(in);
					goddess.setBirthday(birthday);
					System.out.println("������Ů�������");
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("������ĸ�ʽ����,����������");
					step = 3;
				}
			}
			else if(4==step) {
				goddess.setEmail(in);
				System.out.println("������Ů����ֻ���");
			}
			else if(5==step) {
				goddess.setMobile(in);	
				
				try {
					action.add(goddess);
					System.out.println("����Ů���ɹ�");
					break;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("����Ů��ʧ��");
					break;
				}
			}
			step++;
		}
		
	}
	private void get(GoddessAction action ) {
		System.out.println("��ѡ����Ҫ�鿴��Ů��id");
		Scanner in = new Scanner(System.in);
		int id =0;
		while(true) {
			try {
				id = in.nextInt();
				System.out.println(action.get(id).toString());
				break;
			}
			catch(InputMismatchException e){
				System.out.println("������һ��Ů���������");
			}
			catch(Exception e) {
				System.out.println("���������Χ�ڵ�Ů���������");
			}
		}
	}
	private void delete(GoddessAction action ) {
		System.out.println("������ɾ��Ů������к�");
		Scanner in = new Scanner(System.in);
		int id =0;
		try {
			id = in.nextInt();
			action.del(id);
			System.out.println("ɾ���ɹ���");
		}
		catch(InputMismatchException e){
			System.out.println("������һ��Ů���������");
		}
		catch(Exception e) {
			System.out.println("���������Χ�ڵ�Ů���������");
		}
	}
	private void search(GoddessAction action) {
		Scanner in = new Scanner(System.in);
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.println("������Ů����Ϣ��������");
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
			System.out.println("��������ȷָ��");
		}
	}
	public static void main(String[] args) {
		View view = new View();
		
		System.out.println(CONTEXT);
		//��ô���ֳ���ĳ�������
		Scanner scan =new Scanner(System.in);
		GoddessAction action = new GoddessAction();
		while(scan.hasNext()) {
			String in = scan.next().toString();
			
			if(OPERATION_EXIT.equals(in.toUpperCase())
					||OPERATION_EXIT.substring(0, 1).equals(in.toUpperCase())) {
				System.out.println("���Գɹ��˳�Ů������");
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
				System.out.println("�������ֵΪ��"+in);
			}
			
			
			
		}

	}

}
