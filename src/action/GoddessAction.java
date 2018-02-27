package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.goddessDao;
import model.Goddess;

public class GoddessAction {

	public void add(Goddess goddess) throws Exception {
		goddessDao dao = new goddessDao();
		dao.addGoddess(goddess);
	}
	public void edit(Goddess goddess) throws Exception {
		goddessDao dao = new goddessDao();
		dao.updateGoddess(goddess);
	}
	public void del(Integer id) throws Exception {
		goddessDao dao = new goddessDao();
		dao.delGoddess(id);
	}
	public List<Goddess> query() throws Exception {
		goddessDao dao = new goddessDao();
		return dao.query();
	}
	public List<Goddess> query(List<Map<String,Object>> params ) throws Exception {
		goddessDao dao = new goddessDao();
		return dao.query(params);
	}
	public Goddess get(Integer id) throws Exception {
		goddessDao dao = new goddessDao();
		return dao.get(id);
	}

}
