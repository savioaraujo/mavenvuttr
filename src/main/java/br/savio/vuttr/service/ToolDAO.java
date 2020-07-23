/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savio.vuttr.service;

import br.savio.vuttr.basic.service.GenericDAOImpl;
import br.savio.vuttr.model.Tool;
import br.savio.vuttr.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * <p> DAO para a classe Tag </p>
 *
 * @author evaldosavio
 */
@Stateless
public class ToolDAO extends GenericDAOImpl<Tool> {

    /**
     * <p> Consulta as Tools no sistema, caso seja informado o parametro tag a
     * consulta ira filtrar as Tools que possuam aquela tag </p>
     *
     * @param tag : Tag para efetuar a consulta
     * @return Lista contendo todas as Tools que possuam a tag informada,
     * caso nao seja informado a tag a consulta retornara todas as Tools ativas.
     */
    public List<Tool> findByTags(String tag) {
        String sql = "SELECT tool "
                + " FROM Tool tool "
                + " LEFT JOIN tool.tags tag"
                + " WHERE tool.active = TRUE";
        if (tag != null) {
            sql += " AND lower(tag.name) = :param";
        }

        Query query = getEntityManager().createQuery(sql);
        if (tag != null) {
            query.setParameter("param", tag);
        }
        return query.getResultList();
    }

    public Tool verifyExists(Tool tool) {
        String sql = "SELECT tool FROM Tool tool WHERE tool.active = TRUE AND lower(tool.title) = :title";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("title", tool.getTitle().trim().toLowerCase());
        List<Tool> result = query.getResultList();
        if (Utils.isEmpty(result)) {
            return tool;
        } else {
            return result.get(0);
        }
    }
}
