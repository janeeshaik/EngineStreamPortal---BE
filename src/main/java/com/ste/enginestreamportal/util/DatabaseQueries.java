package com.ste.enginestreamportal.util;

/**
 * @author Jani Shaik on 18-Dec-2020
 */

public class DatabaseQueries {

    public static final String MATERIAL_BATCH_QUERY = "select a.*, b.id as batchId, b.qty from Material a "+
            "inner join (select p.*,q.qty from Batch p inner join" +
            "(select Material, SUM(Quantity) qty from Batch group by Material) q on p.Material = q.Material) b " +
            "on a.id = b.Material";

}
