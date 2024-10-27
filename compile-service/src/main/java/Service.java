import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.sushkpavel.service.CompileServiceImpl;
import kotlin.coroutines.CoroutineContextImplKt;

import java.io.IOException;

public class Service {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(8083).addService(new CompileServiceImpl()).build().start();
        server.awaitTermination();
    }
}
