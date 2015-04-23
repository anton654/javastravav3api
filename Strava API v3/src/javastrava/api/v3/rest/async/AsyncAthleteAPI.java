package javastrava.api.v3.rest.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.service.AthleteService;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * <p>
 * API definitions for implementation of {@link AthleteService}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface AsyncAthleteAPI {
	/**
	 * @see javastrava.api.v3.service.AthleteService#getAthlete(java.lang.Integer)
	 *
	 * @param athleteId Athlete identifier
	 * @param callback Callback to be executed once the call is completed
	 * @throws NotFoundException If the athlete doesn't exist
	 */
	@GET("/athletes/{id}")
	public void getAthlete(@Path("id") final Integer athleteId, final StravaAPICallback<StravaAthlete> callback) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#getAuthenticatedAthlete()
	 *
	 * @param callback Callback to be executed once the call is completed
	 */
	@GET("/athlete")
	public void getAuthenticatedAthlete(final StravaAPICallback<StravaAthlete> callback);

	/**
	 * @see javastrava.api.v3.service.AthleteService#listAthleteFriends(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @param callback Callback to be executed once the call is completed
	 * @throws NotFoundException If the athlete with the given id doesn't exist
	 * @throws BadRequestException If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/friends")
	public void listAthleteFriends(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaAthlete[]> callback) throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#listAthleteKOMs(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @param callback Callback to be executed once the call is completed
	 * @throws NotFoundException If the athlete doesn't exist
	 * @throws BadRequestException If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/koms")
	public void listAthleteKOMs(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaSegmentEffort[]> callback)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#listAthletesBothFollowing(Integer, javastrava.util.Paging)
	 *
	 * @param athleteId Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @param callback Callback to be executed once the call is completed
	 * @throws NotFoundException If the athlete with the given id doesn't exist
	 * @throws BadRequestException If the paging instructions are invalid
	 */
	@GET("/athletes/{id}/both-following")
	public void listAthletesBothFollowing(@Path("id") final Integer athleteId, @Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaAthlete[]> callback)
			throws NotFoundException, BadRequestException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#listAuthenticatedAthleteFriends(javastrava.util.Paging)
	 *
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @param callback Callback to be executed once the call is completed
	 * @throws BadRequestException If the paging instructions are invalid
	 */
	@GET("/athlete/friends")
	public void listAuthenticatedAthleteFriends(@Query("page") final Integer page, @Query("per_page") final Integer perPage, final StravaAPICallback<StravaAthlete[]> callback) throws BadRequestException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#statistics(Integer)
	 *
	 * @param athleteId Athlete identifier
	 * @param callback Callback to be executed once the call is completed
	 * @throws NotFoundException If the identified athlete doesn't exist
	 */
	@GET("/athletes/{id}/stats")
	public void statistics(@Path("id") final Integer athleteId, StravaAPICallback<StravaStatistics> callback) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteService#updateAuthenticatedAthlete(String, String, String, StravaGender, Float)
	 *
	 * @param city City the athlete is from
	 * @param state State/county/territory/canton/departement/whatever the athlete is from
	 * @param country Country the athlete is from
	 * @param sex Gender of the athlete
	 * @param weight Weight in kilograms
	 * @param callback Callback to be executed once the call is completed
	 */
	@PUT("/athlete")
	public void updateAuthenticatedAthlete(@Query("city") final String city, @Query("state") final String state, @Query("country") final String country,
			@Query("sex") final StravaGender sex, @Query("weight") final Float weight, final StravaAPICallback<StravaAthlete> callback);

}
