package interceptors;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import utils.ReflectUtil;


/**
 * prepare ??? 只拦截分页方法
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DialectStatementHandlerInterceptor implements Interceptor {


    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invocation
                .getTarget();
        Object obj = ReflectUtil.getFieldValue(statement, "delegate");
        if (obj.getClass() == PreparedStatementHandler.class) {
            PreparedStatementHandler handler = (PreparedStatementHandler) ReflectUtil
                    .getFieldValue(statement, "delegate");
            RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(handler,
                    "rowBounds");
            if (rowBounds.getLimit() > 0
                    && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
                BoundSql boundSql = statement.getBoundSql();
                String sql = boundSql.getSql();

                sql = "select * from(select t.*,rownum rn from(" + sql
                        + ") t where rownum <= " + (rowBounds.getOffset() +rowBounds.getLimit()) + ") where rn > " + rowBounds.getOffset();
                ReflectUtil.setFieldValue(boundSql, "sql", sql);
            }
        } else {

        }
        return invocation.proceed();
    }


    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    public void setProperties(Properties arg0) {
    }

}
