--name,sound,scaleX,scaleY,x,y,rotation
local M = {}
local touchable = require("src.Touchable");

local function ParseCSVLine (line,sep) 
	local res = {}
	local pos = 1
	sep = sep or ','
	while true do 
		local c = string.sub(line,pos,pos)
		if (c == "") then break end
		if (c == '"') then
			-- quoted value (ignore separator within)
			local txt = ""
			repeat
				local startp,endp = string.find(line,'^%b""',pos)
				txt = txt..string.sub(line,startp+1,endp-1)
				pos = endp + 1
				c = string.sub(line,pos,pos) 
				if (c == '"') then txt = txt..'"' end 
				-- check first char AFTER quoted string, if it is another
				-- quoted string without separator, then append it
				-- this is the way to "escape" the quote char in a quote. example:
				--   value1,"blub""blip""boing",value3  will result in blub"blip"boing  for the middle
			until (c ~= '"')
			table.insert(res,txt)
			assert(c == sep or c == "")
			pos = pos + 1
		else	
			-- no quotes used, just look for the first separator
			local startp,endp = string.find(line,sep,pos)
			if (startp) then 
				table.insert(res,string.sub(line,pos,startp-1))
				pos = endp + 1
			else
				-- no separator found -> use rest of string and terminate
				table.insert(res,string.sub(line,pos))
				break
			end 
		end
	end
	return res
end

local function loadPage(filename)
	print(filename)
	-- Path for the file to read
	local path = system.pathForFile( "pages/"..filename, system.ResourceDirectory)
	 
	-- Open the file handle
	local file, errorString = io.open( path, "r" )
	 
	if not file then
	    -- Error occurred; output the cause
	    print( "File error: " .. errorString )
	else
	    -- Read data from file
	    --Read and create background
	    local line1 = file:read( "*l" ) -- line 1 is background
	    local background;
	    local backgroundData = ParseCSVLine(line1);
	    if(backgroundData[1] ~= "none") then
	    	background = display.newImage("images/".. backgroundData[1]);
	    	background.x = display.contentCenterX;
	    	background.y = display.contentCenterY;
	    end
	    if(backgroundData[2] ~= "none") then
	    	background.xScale = backgroundData[2];
	    end
	   	if(backgroundData[3] ~= "none") then
	    	background.yScale = backgroundData[3];
	    end
	    --Read and create foreground
	    local line2 = file:read( "*l" )	-- line 2 is foreground
	    local foreground;
	    local foregroundData = ParseCSVLine(line2);
	    if(foregroundData[1] ~= "none") then
	    	foreground = display.newImage("images/"..foregroundData[1]);
	    end
	    if(foregroundData[2] ~= "none") then
	    	foreground.xScale = foregroundData[2];
	    end
	   	if(foregroundData[3] ~= "none") then
	    	foreground.yScale = foregroundData[3];
	    end

	    --Read and create all touchables
	    local touchables = {};
	    local i = 1;
	    for line in file:lines() do
	    	local res = ParseCSVLine(line);
	    	if(res[1] ~= "none" and res[2] ~= "none") then
	    		touchables[i] = touchable:createTouchable(res[1], res[2]);
	    	else
	    		touchables[i] = touchable:createTouchable(res[1]);
			end
	    	if(res[3] ~= "none") then
				touchables[i].xScale = res[3];
	    	end
	    	if(res[4] ~= "none") then
	    		touchables[i].yScale = res[4];
	    	end
			if(res[5] ~= "none") then
				touchables[i].x = res[5];
			end
			if(res[6] ~= "none") then
				touchables[i].y = res[6];
			end
			if(res[7] ~= "none") then
				touchables[i].rotation = res[7];
			end
	    end

	end
	 
	file = nil
	return touchables, background, foreground
end

M.loadPage = loadPage;
return M;