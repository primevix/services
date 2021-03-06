/*
 * EncomendaZ
 * 
 * Copyright (c) 2011, EncomendaZ <http://encomendaz.net>.
 * All rights reserved.
 * 
 * EncomendaZ is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.encomendaz.services.monitoring;

import static net.encomendaz.services.Constants.JSON_MEDIA_TYPE;
import static net.encomendaz.services.Response.Status.OK;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.encomendaz.services.EncomendaZException;
import net.encomendaz.services.Response;
import net.encomendaz.services.util.Booleans;
import net.encomendaz.services.util.Serializer;

@Path("/monitoring.json")
@Produces(JSON_MEDIA_TYPE)
public class MonitoringService {

	@PUT
	public String register(@FormParam("clientId") String clientId, @FormParam("trackId") String trackId,
			@FormParam("label") String label, @FormParam("callback") String callback) throws EncomendaZException {

		MonitoringManager.register(clientId, trackId, label);

		Response<String> response = new Response<String>();
		response.setStatus(OK);

		return Serializer.json(response, callback);
	}

	@GET
	public String search(@QueryParam("clientId") String clientId, @QueryParam("trackId") String trackId,
			@QueryParam("completed") String completed, @QueryParam("unread") String unread,
			@QueryParam("callback") String callback) throws MonitoringException {

		MonitoringManager.validateClientId(clientId);

		List<Monitoring> list = MonitoringManager.search(clientId, trackId, Booleans.valueOf(completed),
				Booleans.valueOf(unread));

		Response<List<Monitoring>> response = new Response<List<Monitoring>>();
		response.setStatus(OK);
		response.setData(list);

		return Serializer.json(response, callback);
	}

	@DELETE
	public String delete(@QueryParam("clientId") String clientId, @QueryParam("trackId") String trackId,
			@QueryParam("callback") String callback) throws MonitoringException {

		MonitoringManager.delete(clientId, trackId);

		Response<String> response = new Response<String>();
		response.setStatus(OK);

		return Serializer.json(response, callback);
	}
}
