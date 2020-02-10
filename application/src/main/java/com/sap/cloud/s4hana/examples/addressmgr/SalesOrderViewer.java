package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Strings;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import org.slf4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;


@WebServlet("/sov")
public class SalesOrderViewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(SalesOrderViewer.class);

    private Destination destination;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");

        final String jsonResult = "";
        try {
        	
        	destination = DestinationAccessor.getDestination("HEC");
        	/*
        	final List<MySO> salesOrders = ODataQueryBuilder
        	        .withEntity("/sap/opu/odata/sap/ZWEDLV_SO_SRV",
        	                "ZWEDLV_SO")
        	        .select("so_id",
        	                "currency_code",
        	                "gross_amount")
        	        .build()
        	        .execute(destination)
        	        .asList(SalesOrderType.class);
        	        */
        	
        	
            if (id == null) {
                logger.info("Retrieving all Sales Orders");
                
                //jsonResult = new Gson().toJson(result);
            } else {
                if (!validateInput(id)) {
                    logger.warn("Invalid request to retrieve a business partner, id: {}.", id);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            String.format("Invalid business partner ID '%s'. " +
                                            "Business partner ID must not be empty or longer than 10 characters.",
                                    id));
                    return;
                }
                logger.info("Retrieving Sales Orders Data", id);

                //jsonResult = new Gson().toJson(result);
            }

            response.setContentType("application/json");
            response.getWriter().write(jsonResult);
        } catch (Exception e) {
            logger.error("Error occured while handling request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occured while handling request: " + e.toString());
        }
    }

    private boolean validateInput(String id) {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }
}
