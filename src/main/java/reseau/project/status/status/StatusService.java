package reseau.project.status.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;
import reseau.project.status.statusPreview.StatusPreview;
import reseau.project.status.statusPreview.StatusPreviewRepository;
import reseau.project.status.users.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class StatusService implements StatusInterface{

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusPreviewRepository statusPreviewRepository;

    @Override
    public Status addAStatus(int userNumber, String statusCaption, String statusText, MultipartFile statusImage, MultipartFile statusVideo, boolean isPublicStatus, String disappearTime) throws IOException {
        byte[] theStatusImage = null;
        byte[] theStatusVideo = null;
        if(!(userNumber > 0)) throw new BadRequestException("A userNumber form data field is required");
        if(userNumber < 600000000 || userNumber > 700000000) throw new BadRequestException("The userNumber is not a valid phone number");
        if(!userRepository.findById(userNumber).isPresent()) throw new NotFoundException("The user with that Number is not registered to this platform");
        LocalDateTime theDisappearTime = null;

        if( statusImage  == null &&  statusText  == null &&  statusVideo  == null){
            throw new BadRequestException("You status has to contain a text, image or video");
        }

        int numOfNulls = 0;
        if(statusText == null) numOfNulls++;
        if(statusImage == null) numOfNulls++;
        if(statusVideo == null) numOfNulls++;

        if(numOfNulls != 2) throw new BadRequestException("Your status can be only one of these: text, image or video with a caption");

        if(disappearTime == null){            //disappear time comes in the body as a string
            theDisappearTime = LocalDateTime.now().plusDays(1);
        }else{
            theDisappearTime = LocalDateTime.parse(disappearTime);
        }

        LocalDateTime thePostTime = LocalDateTime.now();
        String theStatusCaption = null;
        if(!( statusCaption == null)) theStatusCaption = statusCaption ;
        String theStatusText = null;
        if(!(statusText == null)) theStatusText = statusText ;

        if(!(statusImage == null)){
            String imageFileName = StringUtils.cleanPath(statusImage.getOriginalFilename());
            String imageFileExtension = StringUtils.getFilenameExtension(imageFileName);
            if(!(imageFileExtension.equalsIgnoreCase("png") || imageFileExtension.equalsIgnoreCase("jpg"))) throw new BadRequestException("You need to provide an image here");
            if (imageFileName.contains("..")) {
                throw new BadRequestException("Filename contains invalid path sequence "
                        + imageFileName);
            }
            theStatusImage = statusImage.getBytes();
        }

        if(!(statusVideo == null)){
            String videoFileName = StringUtils.cleanPath(statusVideo.getOriginalFilename());
            String videoFileExtension = StringUtils.getFilenameExtension(videoFileName);
            if(!videoFileExtension.equalsIgnoreCase("mp4") ) throw new BadRequestException("You need to provide a video here");
            if (videoFileName.contains("..")) {
                throw new BadRequestException("Filename contains invalid path sequence "
                        + videoFileName);
            }
            theStatusVideo = statusVideo.getBytes();
        }

        Status status = new Status(userNumber, thePostTime, theStatusCaption, theStatusText, theStatusImage, theStatusVideo, isPublicStatus, theDisappearTime );
        Optional<StatusPreview> optionalStatusPreview = statusPreviewRepository.findById(userNumber);
        int numberOfStatus = 1;
        if(optionalStatusPreview.isPresent()){
            numberOfStatus = optionalStatusPreview.get().getNumberOfStatus();
                    numberOfStatus++;

        }
        Status returnedStatus = statusRepository.save(status);
        statusPreviewRepository.save(new StatusPreview(userNumber, numberOfStatus, null, thePostTime));
        return returnedStatus;
    }

    @Override
    public Status getStatusByUserAndPostTime(int userNumber, LocalDateTime postTime) {
        Optional<Status> optionalStatus = statusRepository.findByUserNumberAndPostTime(userNumber, postTime);
        if(optionalStatus.isPresent()){
            return optionalStatus.get();
        }else {
            throw new NotFoundException("That user did not post a status image at that time");
        }
    }

    @Override
    public List<Status> getStatusOfAUser(int userNumber) {
        if(!(userNumber > 0)) throw new BadRequestException("A userNumber form data field is required");
        if(userNumber < 600000000 || userNumber > 700000000) throw new BadRequestException("The userNumber is not a valid phone number");
 return statusRepository.findAllByUserNumber(userNumber);
    }

    @Override
    public List<Status> getStatusOfAllUsers() {
        return statusRepository.findAll();
    }
}
