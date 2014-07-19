
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IQuesTeamRelaDAO;

import com.school.entity.teachpaltform.QuesTeamRela;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IQuesTeamRelaManager;
import com.school.util.PageResult;

@Service
public class  QuesTeamRelaManager extends BaseManager<QuesTeamRela> implements IQuesTeamRelaManager  {

    private IQuesTeamRelaDAO questeamreladao;

    @Autowired
    @Qualifier("quesTeamRelaDAO")
    public void setQuesteamreladao(IQuesTeamRelaDAO questeamreladao) {
        this.questeamreladao = questeamreladao;
    }

    public Boolean doSave(QuesTeamRela questeamrela) {
        return this.questeamreladao.doSave(questeamrela);
    }

    public Boolean doDelete(QuesTeamRela questeamrela) {
        return this.questeamreladao.doDelete(questeamrela);
    }

    public Boolean doUpdate(QuesTeamRela questeamrela) {
        return this.questeamreladao.doUpdate(questeamrela);
    }

    public List<QuesTeamRela> getList(QuesTeamRela questeamrela, PageResult presult) {
        return this.questeamreladao.getList(questeamrela,presult);
    }

    public List<Object> getSaveSql(QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
        return this.questeamreladao.getSaveSql(questeamrela,sqlbuilder);
    }

    public List<Object> getDeleteSql(QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
        return this.questeamreladao.getDeleteSql(questeamrela,sqlbuilder);
    }

    public List<Object> getUpdateSql(QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
        return this.questeamreladao.getUpdateSql(questeamrela,sqlbuilder);
    }

    public Boolean doExcetueArrayProc(List<String> sqlArrayList,
                                      List<List<Object>> objArrayList) {
        return this.questeamreladao.doExcetueArrayProc(sqlArrayList,objArrayList);
    }

    public QuesTeamRela getOfExcel(Sheet rs, int cols, int d, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ICommonDAO<QuesTeamRela> getBaseDAO() {
        // TODO Auto-generated method stub
        return questeamreladao;
    }

    @Override
    public String getNextId() {
        // TODO Auto-generated method stub
        return null;
    }
}

