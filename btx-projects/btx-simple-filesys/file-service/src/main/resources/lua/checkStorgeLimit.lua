local now = redis.call('HGET', KEYS[0], ARGV[0]) +  ARGV[1]
local limit = redis.call('HGET', KEYS[1], ARGV[0])
local ret = false
if now <= limit then
    ret = true
    redis.call('HSET',KEYS[0], ARGV[0],now)
end
return ret