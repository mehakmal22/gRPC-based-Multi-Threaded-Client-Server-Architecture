import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import imagesearch.ImageSearchGrpc;
import imagesearch.KeywordRequest;
import imagesearch.KeywordResponse;

public class ImageSearchClient {

    private final ManagedChannel channel;
    private final ImageSearchGrpc.ImageSearchBlockingStub blockingStub;

    public ImageSearchClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build());
    }

    ImageSearchClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = ImageSearchGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void getImageFromServer(String keyword) {
        try {
            KeywordRequest request = KeywordRequest.newBuilder().setKeyword(keyword).build();
            KeywordResponse response = blockingStub.searchForKeyword(request);

            String outputDir = "received_images_grpc";
            java.io.File dir = new java.io.File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String outputPath = outputDir + java.io.File.separator + keyword + ".jpg";
            try (FileOutputStream imgFile = new FileOutputStream(outputPath)) {
                imgFile.write(response.getImageData().toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Received image saved at: " + outputPath);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        try {
            ImageSearchClient client = new ImageSearchClient(host, port);
            try {
                String keyword = ""; // Input your keyword here
                client.getImageFromServer(keyword);
            } finally {
                client.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
