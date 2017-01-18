package spider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.mapper.DouBanUserMapper;
import spider.model.DouBanUser;

/**
 *
 * @author shiwei
 * @version $$Id: DouBanUserService, V 0.1 2017/1/18 14:25 shiwei Exp $$
 */
@Service
public class DouBanUserService {
    @Autowired
    DouBanUserMapper douBanUserMapper;

    public int insertAndGetId(DouBanUser douBanUser)
    {
        return douBanUserMapper.insertAndGetId(douBanUser);
    }
}
